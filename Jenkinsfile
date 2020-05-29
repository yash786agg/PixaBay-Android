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

    stage('Debug') {
      steps {
              sh './gradlew assembleDebug'
      }
    }

    stage('Unit Test') {
       steps {
              sh './gradlew testDebugUnitTest'
       }
    }
  }
}