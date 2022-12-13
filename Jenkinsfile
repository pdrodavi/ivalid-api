pipeline {
  agent any
  stages {
    stage('Input') {
      steps {
        git 'https://github.com/pdrodavi/ivalid-api.git'
      }
    }

  }
  environment {
    maven = 'M3'
    dockerTool = 'Docker'
  }
}