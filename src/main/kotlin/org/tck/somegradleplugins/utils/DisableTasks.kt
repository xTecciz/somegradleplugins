package org.tck.somegradleplugins.utils

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionGraph
import org.gradle.api.logging.Logger
import org.gradle.kotlin.dsl.extra

fun Project.ensureUnreachableTasksDisabled() {
    checkIsRootProject()

    if (extra.has(DISABLE_TASK_PROPERTY)) return

    extra.set(DISABLE_TASK_PROPERTY, true)

    gradle.taskGraph.whenReady {
        DisableTasks(graph = this, logger = logger).disableTasks()
    }
}

private class DisableTasks(
    private val graph: TaskExecutionGraph,
    private val logger: Logger,
) {
    private val rootTasks = graph.allTasks
        .filterNot { task -> graph.allTasks.any { task in graph.getDependencies(it) } }

    private val results = mutableMapOf<Pair<Task, Task>, Boolean>()

    fun disableTasks() {
        graph.allTasks
            .filterNot { it.enabled }
            .forEach(::disableChildren)
    }

    private fun disableChildren(task: Task) {
        graph.getDependencies(task)
            .filter { it.enabled }
            .forEach { child ->
                if (isTaskAccessible(child).not()) {
                    child.enabled = false
                    logger.info("Task disabled: ${child.path}")
                    disableChildren(child)
                } else {
                    logger.info("Task accessible: ${child.path}")
                }
            }
    }

    private fun isTaskAccessible(task: Task): Boolean =
        rootTasks.any { rootTask ->
            (rootTask != task && isPathExists(rootTask, task)).also {
                if (it) logger.info("Task $task accessible from $rootTask")
            }
        }

    private fun isPathExists(source: Task, destination: Task): Boolean =
        results.getOrPut(source to destination) {
            when {
                !source.enabled -> false
                source == destination -> true.also { logger.info("Task reached: $destination") }
                else -> graph.getDependencies(source).any { isPathExists(it, destination) }
                    .also { if (it) logger.info("Task path found from $source to $destination") }
            }
        }
}

private const val DISABLE_TASK_PROPERTY = "org.tck.gradle.disableTasks"