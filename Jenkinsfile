pipeline {
agent any

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