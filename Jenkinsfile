pipeline {
  agent any
  stages {
    stage('Checkout') {
      agent any
      steps {
        git(url: 'https://github.com/pdrodavi/ivalid-api.git', branch: 'jfrog')
      }
    }

    stage('Build') {
      steps {
        sh 'sh \'mvn spring-boot:build-image\''
      }
    }

    stage('Docker Auth') {
      agent any
      steps {
        sh 'docker login -uinfrarq@srvex.com.br srvextechnology.jfrog.io'
        input 'Password'
      }
    }

    stage('') {
      steps {
        input(message: 'Image ID', id: 'img')
        input(message: 'Docker Image', id: 'dk-img')
        input(message: 'Docker Tag', id: 'dk-tag')
      }
    }

  }
  environment {
    maven = 'M3'
  }
}