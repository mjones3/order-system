output "aws_lambda_function_order_service_arn" {
  description = "The ARN of the ECS Task Execution Role"
  value       = aws_lambda_function.order_service.arn
}

output "aws_lambda_function_inventory_service_arn" {
  description = "The ARN of the ECS Task Execution Role"
  value       = aws_lambda_function.inventory_service.arn
}

output "aws_lambda_function_email_service_arn" {
  description = "The ARN of the ECS Task Execution Role"
  value       = aws_lambda_function.email_service.arn
}

output "aws_lambda_function_order_callback_service_arn" {
  description = "The ARN of the ECS Task Execution Role"
  value       = aws_lambda_function.order_callback.arn
}
