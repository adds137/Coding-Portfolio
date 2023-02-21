provider "aws" {
  region     = "ap-southeast-2"
  access_key = "AKIAZT43JW54NJMBOE6Z"
  secret_key = "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"
}


resource "aws_security_group" "rds_sg" {
  name        = "rds_security_group"
  description = "allows access for postgres"

  ingress {
    description      = "alllows postgres access"
    from_port        = 5432
    to_port          = 5432
    protocol         = "tcp"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

  egress {
    description      = "allow data to be sent from postgres"
    from_port        = 5432
    to_port          = 5432
    protocol         = "tcp"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }
}

resource "aws_db_instance" "peterMacDatabase" {
  allocated_storage           = 10
  allow_major_version_upgrade = true
  apply_immediately           = true
  db_name                     = "peterMacDb"
  engine                      = "postgres"
  instance_class              = "db.t4g.micro"
  username                    = "peterMacAdmin"
  password                    = "yu&hlsweKL2#l_&fRuH+"
  publicly_accessible         = true
  identifier                  = "petermac-database"
  final_snapshot_identifier   = "petermac-db-finalsnapshot"
  vpc_security_group_ids      = [aws_security_group.rds_sg.id]
}

resource "aws_s3_bucket" "peterMacS3Bucket" {
  bucket = "petermac-bucket"
}

resource "aws_s3_bucket_versioning" "peterMac-S3Bucket_versioning" {
  bucket = aws_s3_bucket.peterMacS3Bucket.id
  versioning_configuration {
    status = "Enabled"
  }
}
