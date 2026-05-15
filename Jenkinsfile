pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK21'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/sattiarati-creator/oee.git',
                    credentialsId: 'github-token'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }

        stage('Run Application') {
            steps {
                sh 'mvn exec:java -Dexec.mainClass="com.example.app.PasswordChecker"'
            }
        }
    }

    post {

        success {
            emailext (
                subject: "SUCCESS: ${JOB_NAME} #${BUILD_NUMBER}",
                body: """
Build completed successfully.

Project: Password Strength Checker
Status : SUCCESS

Check Build:
${BUILD_URL}
""",
                to: "sattiarati@gmail.com"
            )
        }

        failure {
            emailext (
                subject: "FAILED: ${JOB_NAME} #${BUILD_NUMBER}",
                body: """
Build failed.

Project: Password Strength Checker
Status : FAILED

Check Build:
${BUILD_URL}
""",
                to: "sattiarati@gmail.com"
            )
        }
    }
}
