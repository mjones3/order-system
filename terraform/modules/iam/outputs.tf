output "ecs_task_execution_role_arn" {
  description = "The ARN of the ECS Task Execution Role"
  value       = aws_iam_role.ecs_task_execution_role.arn
}

# output "ecs_task_execution_role_arn" {
#   description = "The name of the ECS Task Execution Role"
#   value       = aws_iam_role.ecs_task_execution_role.arn
# }
