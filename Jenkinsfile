pipeline {
    agent any 

    tools {
        jdk 'JDK-21'
        maven 'Maven-3'
    }

    environment {
        DOCKER_IMAGE = 'deepovi164/my-todo-app:${env.BUILD_NUMBER}'
        MAVEN_REPOSITORY = '${env.WORKSPACE}/.m2/repository'
    }

    stages{
        stage('checkout'){
            steps {
                git branch: 'master', credentialsId: 'github', url: 'https://github.com/B-Pavitro1/todo-java-maven-app'
                echo 'code checked out successfully'
            }
        }

        stage('maven build'){
            steps {
                script {
                    sh """
                        mkdir -p $MAVEN_REPOSITORY
                        mvn clean package -DskipTests \
                        -Dmaven.repo.local=${MAVEN_REPOSITORY} \
                        -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn \
                        -B
                    """
                }
            }
        }

        stage('build docker image'){
            steps {
                script {
                    sh 'docker build -t $DOCKER_IMAGE .'
                    echo 'docker image build successfully'
                }
            }
        }

        stage('push docker image to docker registry'){
            steps {
                script {
                    docker.withRegistry('','docker-hub-cred'){
                        sh 'docker push $DOCKER_IMAGE'
                        echo 'docker image pushed to docker registry successfully'
                    }
                }
            }
        }

        stage('deploy docker image to EC2 instance'){
            steps {
                script {
                    def DOCKER_IMAGE = "deepovi164/my-todo-app:${env.BUILD_NUMBER}"
                    def EC2_HOST = 'ec2-100-52-238-46.compute-1.amazonaws.com'

                    withEnv(["DOCKER_IMAGE=${DOCKER_IMAGE}"]) {
                        sshagent(['ec2-deploy-key']) {
                            sh """
                                ssh -o StrictHostKeyChecking=no ec2-user@${EC2_HOST} '
                                    docker pull ${DOCKER_IMAGE} &&
                                    docker stop todo-app || true &&
                                    docker rm todo-app || true &&
                                    docker run -d --name todo-app -p 8080:8080 ${DOCKER_IMAGE}
                                '
                            """
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            cleanWs()
            echo 'Pipeline completed. Workspace cleaned.'
        }
    }
}