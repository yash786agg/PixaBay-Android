pipeline {
agent {
    // Run on a build agent where we have the Android SDK installed
    label 'master'
  }

  stages {
    stage('Clean') {
       steps {
             sh 'clean'
       }
    }

    stage('Debug') {
      steps {
              sh 'assembleDebug'
      }
    }

    stage('Unit Test') {
       steps {
              sh 'testDebugUnitTest'
       }
    }
  }
}