resource "aws_sqs_queue" "orders" {
  name                       = "orders-queue"
  delay_seconds              = 0
  max_message_size           = 262144
  message_retention_seconds  = 345600
  visibility_timeout_seconds = 30

  tags = {
    Environment = "dev"
    Project     = "order-system"
  }
}
