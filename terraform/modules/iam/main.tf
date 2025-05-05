# 1) An IAM policy document that allows ECS tasks to assume the role.
data "aws_iam_policy_document" "ecs_task_execution_assume_role" {
  statement {
    effect  = "Allow"
    actions = ["sts:AssumeRole"]
    principals {
      type        = "Service"
      identifiers = ["ecs-tasks.amazonaws.com"]
    }
  }
}

# 2) The IAM role that the ECS task will assume.
resource "aws_iam_role" "ecs_task_execution_role" {
  name               = "ecsTaskExecution"
  assume_role_policy = data.aws_iam_policy_document.ecs_task_execution_assume_role.json
}

# 3) Attach the AWS-managed policy that covers:
#    - Pulling images from ECR
#    - Sending logs to CloudWatch
resource "aws_iam_role_policy_attachment" "ecs_task_execution_role_policy_attachment" {
  role       = aws_iam_role.ecs_task_execution_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"

}

resource "aws_iam_role_policy" "ecs_task_execution_sqs_policy" {
  name = "ecs-task-execution-sqs-policy"
  role = aws_iam_role.ecs_task_execution_role.id

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect   = "Allow",
        Action   = "sqs:SendMessage",
        Resource = "arn:aws:sqs:us-east-1:294417223953:orders-queue"
      }
    ]
  })
}


resource "aws_iam_role" "sfn_role" {
  name = "order-saga-sfn-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect    = "Allow",
        Principal = { Service = "states.amazonaws.com" },
        Action    = "sts:AssumeRole"
      }
    ]
  })
  tags = {
    Environment = "dev"
    Project     = "order-system"
  }
}

resource "aws_iam_role" "lambda_exec_role" {
  name = "lambda_exec_role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect = "Allow",
        Principal = {
          Service = "lambda.amazonaws.com"
        },
        Action = "sts:AssumeRole"
      }
    ]
  })

  tags = {
    Environment = "dev"
    Project     = "order-system"
  }
}

resource "aws_iam_role_policy_attachment" "lambda_logging" {
  role       = aws_iam_role.lambda_exec_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole"
}


resource "aws_iam_role_policy" "sfn_policy" {
  name = "order-saga-sfn-policy"
  role = aws_iam_role.sfn_role.id

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect = "Allow",
        Action = [
          "lambda:InvokeFunction"
        ],
        Resource = [
          var.aws_lambda_function_order_service_arn,
          var.aws_lambda_function_inventory_service_arn,
          var.aws_lambda_function_payment_service_arn,
          var.aws_lambda_function_release_inventory_arn,
          var.aws_lambda_function_cancel_order_arn,

        ]
      }
    ]
  })
}
