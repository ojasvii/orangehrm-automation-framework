pipeline {
    agent any

    tools {
        maven 'Maven'        // Must match Global Tool Config name
        jdk 'Java_JDK'       // Must match Global Tool Config name
    }

    triggers {
        pollSCM('H/5 * * * *')   // Poll GitHub repo every 5 minutes
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                url: 'https://github.com/ojasvii/orangehrm-automation-framework.git',
                credentialsId: 'github-cred'
            }
        }

        stage('Build & Test') {
            steps {
                bat 'mvn clean test'
            }
        }

         stage('Publish Reports') {
                    steps {
                        // Archive TestNG Results
                        junit '**/test-output/testng-results.xml'

                        // Archive Extent Report
                        publishHTML([
                            allowMissing: false,
                            alwaysLinkToLastBuild: true,
                            keepAll: true,
                            reportDir: 'reports',          // Path where ExtentManager is saving report
//                             reportFiles: 'ExtentReport.html',
//                             reportName: 'Extent Report'
                             reportFiles: '**/ExecutionReport_*.html',
                             reportName: "Execution Report"
                        ])
                    }
                }
            }

//             post {
//                 always {
//                     echo "Build & Test Completed"
//                 }
//             }

  post {
        always {
            // Publish TestNG/JUnit results
            junit '**/target/surefire-reports/*.xml'

            // Archive Extent Report so you can download if needed
           archiveArtifacts artifacts: 'reports/ExecutionReport_*.html', fingerprint: true
        }
    }
}
