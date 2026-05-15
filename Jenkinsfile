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
                    url: 'https://github.com/sattiarati-creator/oee.git
',
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
                sh '''
                pkill -f PasswordChecker || true
                nohup mvn exec:java -Dexec.mainClass="com.example.app.PasswordChecker" > output.log 2>&1 &
                '''
            }
        }
    }

    post {

        success {
            emailext (
                subject: "SUCCESS: ${JOB_NAME} #${BUILD_NUMBER}",
                body: """
Build completed successfully.

Application URL:
http://localhost:8081

Build URL:
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

Check:
${BUILD_URL}
""",
                to: "sattiarati@gmail.com"
            )
        }
    }
}
