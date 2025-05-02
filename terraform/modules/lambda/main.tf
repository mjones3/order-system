resource "aws_sfn_state_machine" "order_saga" {
  name     = "OrderSagaStateMachine"
  role_arn = var.aws_iam_role_sfn_role_arn

  definition = <<EOF
{
  "Comment": "State machine for processing the order saga",
  "StartAt": "OrderService",
  "States": {
    "OrderService": {
      "Type": "Task",
      "Resource": "arn:aws:states:::lambda:invoke",
      "Parameters": {
        "FunctionName": "${aws_lambda_function.order_service.arn}",
        "Payload": {
          "input.$": "$"
        }
      },
      "Next": "InventoryService"
    },
    "InventoryService": {
      "Type": "Task",
      "Resource": "arn:aws:states:::lambda:invoke",
      "Parameters": {
        "FunctionName": "${aws_lambda_function.inventory_service.arn}",
        "Payload": {
          "input.$": "$"
        }
      },
      "Next": "CallbackToOrder"
    },
    "CallbackToOrder": {
      "Type": "Task",
      "Resource": "arn:aws:states:::lambda:invoke",
      "Parameters": {
        "FunctionName": "${aws_lambda_function.order_callback.arn}",
        "Payload": {
          "input.$": "$"
        }
      },
      "Next": "EmailService"
    },
    "EmailService": {
      "Type": "Task",
      "Resource": "arn:aws:states:::lambda:invoke",
      "Parameters": {
        "FunctionName": "${aws_lambda_function.email_service.arn}",
        "Payload": {
          "input.$": "$"
        }
      },
      "End": true
    }
  }
}
EOF
  tags = {
    Environment = "dev"
    Project     = "order-system"
  }
}


resource "aws_lambda_function" "order_service" {
  function_name = "orderServiceFunction"
  handler       = "lambda_function.lambda_handler" # file name . function name
  runtime       = "python3.9"                      # or your preferred Python version
  role          = var.aws_lambda_assume_role_arn
  filename      = "../apps/functions/order-service-handler/orderServiceFunction.zip" # your deployment package ZIP file
  environment {
    variables = {
      API_ENDPOINT_ORDERS = var.api_endpoint_orders
    }
  }
  tags = {
    Environment = "dev"
    Project     = "order-system"
  }
}

resource "aws_lambda_function" "inventory_service" {
  function_name = "inventoryServiceFunction"
  handler       = "lambda_function.lambda_handler" # file name . function name
  runtime       = "python3.9"                      # or your preferred Python version
  role          = var.aws_lambda_assume_role_arn
  filename      = "../apps/functions/inventory-service-handler/inventoryServiceFunction.zip" # your deployment package ZIP file

  environment {
    variables = {
      API_ENDPOINT_INVENTORY = var.api_endpoint_inventory
    }
  }
  tags = {
    Environment = "dev"
    Project     = "order-system"
  }
}

resource "aws_lambda_function" "order_callback" {
  function_name = "orderCallbackFunction"
  handler       = "lambda_function.lambda_handler" # file name . function name
  runtime       = "python3.9"                      # or your preferred Python version
  role          = var.aws_lambda_assume_role_arn
  filename      = "../apps/functions/order-callback/orderCallbackFunction.zip" # your deployment package ZIP file
  tags = {
    Environment = "dev"
    Project     = "order-system"
  }
}

resource "aws_lambda_function" "email_service" {
  function_name = "emailServiceFunction"
  handler       = "lambda_function.lambda_handler" # file name . function name
  runtime       = "python3.9"                      # or your preferred Python version
  role          = var.aws_lambda_assume_role_arn
  filename      = "../apps/functions/email-service/emailServiceFunction.zip" # your deployment package ZIP file
  tags = {
    Environment = "dev"
    Project     = "order-system"
  }
}
