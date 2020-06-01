pipeline {
agent {
    // Run on a build agent where we have the Android SDK installed
    label 'master'
  }

  stages {
    stage('Clean') {
       steps {
             sh './gradlew clean'
       }
    }

    stage('Compile') {
           steps {
              // Compile the app and its dependencies
                 sh './gradlew compileDebugSources'
           }
        }

    stage('Detekt Lint Analysis') {
               steps {
                  // static analysis using Detekt lint
                     sh './gradlew detekt'
               }
            }

    stage('Unit Test') {
           steps {
               // Compile and run the unit tests for the app and its dependencies
                  sh './gradlew testDebugUnitTest'

               // Analyse the test results and update the build result as appropriate
                  junit '**/TEST-*.xml'
           }
        }

    stage('Build APK') {
      steps {

          // Finish building and packaging the APK
            sh './gradlew assembleDebug'
          // Archive the APKs so that they can be downloaded from Jenkins
            archiveArtifacts '**/*.apk'
      }
    }

    stage('Static analysis') {
            steps {
               // Run Lint and analyse the results
                   sh './gradlew lintDebug'
                   androidLint pattern: '**/lint-results-*.xml'
            }
        }
  }

  post {
        failure {
          // Notify developer team of the failure
          mail to: 'yash.agarwal@digia.com', subject: 'Oops!', body: "Build ${env.BUILD_NUMBER} failed; ${env.BUILD_URL}"
        }
      }
}