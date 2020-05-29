pipeline {
agent {
    // Run on a build agent where we have the Android SDK installed
    label 'android'
  }

  stages {
    stage('Compile') {
       steps {
          withGradle(gradle: 'Gradle 5.6.4') {
                // Compile the app and its dependencies
                sh './gradlew compileDebugSources'
          }
       }
    }

    stage('Unit test') {
      steps {
         withGradle(gradle: 'Gradle 5.6.4') {
             // Compile and run the unit tests for the app and its dependencies
                sh './gradlew testDebugUnitTest testDebugUnitTest'

             // Analyse the test results and update the build result as appropriate
                junit '**/TEST-*.xml'
         }
      }
    }

    stage('Build APK') {
       steps {
         withGradle(gradle: 'Gradle 5.6.4') {
            // Finish building and packaging the APK
               sh './gradlew assembleDebug'

            // Archive the APKs so that they can be downloaded from Jenkins
               archiveArtifacts '**/*.apk'
         }
       }
    }

    stage('Static analysis') {
        steps {
          withGradle(gradle: 'Gradle 5.6.4') {
             // Run Lint and analyse the results
                sh './gradlew lintDebug'
                androidLint pattern: '**/lint-results-*.xml'
          }
        }
    }
  }

  post {
      failure {
        // Notify developer team of the failure
        mail to: 'yash.agarwalL@digia.com', subject: 'Oops!', body: "Build ${env.BUILD_NUMBER} failed; ${env.BUILD_URL}"
      }
    }
}