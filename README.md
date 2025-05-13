# 백엔드 개발 과제 (Java)
📖 프로젝트 개요
이 프로젝트는 Spring Boot를 사용하여 JWT 기반 인증/인가 기능을 구현하고, 이를 AWS EC2 환경에 배포하는 과제입니다. 주요 기능으로는 회원가입, 로그인, 관리자 권한 부여 API가 포함되어 있으며, 각 API는 Swagger UI를 통해 문서화되어 있습니다.
🔧 기술 스택
Java 11
Spring Boot 2.5.x
Spring Security (JWT 인증/인가)
Junit 5 (단위 테스트)
Swagger/OpenAPI (API 문서화)
AWS EC2 (애플리케이션 배포)
💡 기능
<details>
<summary>1. 회원가입 API</summary>

- **HTTP Method**: POST  
- **URL**: /signup  

**Request Body 예시(성공)**:  
![image](https://github.com/user-attachments/assets/6a08094c-298c-4252-9c26-965b3afb6e2f)

**Response Body 예시 (실패: 이미 가입된 사용자)**:  
![image](https://github.com/user-attachments/assets/068e3729-a6c5-450e-af44-5d858f6870f4)

</details>

<details>
<summary>2. 로그인 API</summary>

- **HTTP Method**: POST  
- **URL**: /login  

**Request Body 예시(성공)**:  
![image](https://github.com/user-attachments/assets/5752af4e-6d05-49a3-8ad2-9b0833b2608d)

**Response Body 예시 (실패: 잘못된 계정 정보)**:  
![image](https://github.com/user-attachments/assets/a7b2c3b2-33f8-4051-a2be-e9c80ab8422f)

</details>

<details>
<summary>3. 관리자 권한 부여 API</summary>

- **HTTP Method**: PATCH  
- **URL**: /admin/users/{userId}/roles  

**Response Body 예시 (성공)**:  
![image](https://github.com/user-attachments/assets/f71ab569-3390-467a-aead-2f1f887ce150)

**Response Body 예시 (실패: 접근 권한 없음)**:  
![image](https://github.com/user-attachments/assets/3a8b7447-ada1-4b28-8eb9-c964fa24e652)

</details>
<details>
<summary>🛠 JWT 인증/인가</summary>

- ✅ **JWT 토큰 생성 및 검증**: 유효한 JWT 토큰이 생성되고 검증됩니다.
- ✅ **토큰 만료 및 갱신**: 만료된 토큰은 `INVALID_TOKEN` 에러로 처리됩니다.
- ✅ **권한 확인**: 역할에 따라 API 접근이 제한됩니다 (예: 관리자 권한 필요).
- ✅ **부정적 케이스**: 토큰 없음, 잘못된 형식, 만료된 토큰에 대한 에러 처리 확인.
- ✅ **역할 기반 접근 제어**: 권한 없는 사용자가 관리자 API에 접근할 경우 `ACCESS_DENIED` 에러 발생.

</details>

<details>
<summary>🧪 테스트 코드</summary>

테스트는 정상적인 입력, 잘못된 입력, 권한 부족 등 Controller, Service의 테스트를 진행했습니다.  
![image](https://github.com/user-attachments/assets/a0a0a507-fdf4-478f-8269-f1db8c2707ad)
예시 테스트 케이스
회원가입: 정상적인 사용자 정보와 이미 가입된 사용자에 대한 테스트.
로그인: 올바른 자격 증명과 잘못된 자격 증명에 대한 테스트.
관리자 권한 부여: 관리자가 요청할 때와 일반 사용자가 요청할 때의 처리 결과 비교.

</details>

<details>
<summary>📚 API 문서화 (Swagger UI)</summary>

Swagger UI를 사용하여 API 문서를 자동으로 생성하고, 웹 브라우저에서 API 명세서를 쉽게 확인할 수 있습니다.  
Swagger UI URL: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

<details>
<summary>1. 회원가입 요청, 응답</summary>
**요청 예시**:
  
![회원가입 요청](https://github.com/user-attachments/assets/3020f805-a1a4-4767-a86f-4182aae315f1)

**응답 예시**:  
![회원가입 응답](https://github.com/user-attachments/assets/2344b884-c064-4f3d-9d6a-a085e6235463)

</details>

<details>
<summary>2. 로그인 요청, 응답</summary>
**요청 예시**:  

![로그인 요청](https://github.com/user-attachments/assets/9ef12faa-08c5-4297-b006-8b6a924f84b6)

**응답 예시**:  
![로그인 응답](https://github.com/user-attachments/assets/e0a990e9-122a-43e0-936d-5dd0129e2a10)

</details>

<details>
<summary>3. 회원탈퇴 요청, 응답</summary>
**요청 예시**:  
  
![회원탈퇴 요청](https://github.com/user-attachments/assets/9365360c-25e0-4338-abf0-4b6dbf46f8df)

</details>

</details>



☁️ 배포
<!--
1. AWS EC2에 배포
EC2 인스턴스를 생성하고 필요한 환경을 설정합니다.
git clone을 통해 소스 코드를 EC2로 가져온 후, Maven/Gradle을 사용해 프로젝트를 빌드하고 JAR 파일을 생성하여 실행합니다.
2. 배포 방법
방법 1: EC2에 소스코드를 클론하여 빌드 후 실행.
방법 2: 로컬에서 빌드한 JAR 파일을 EC2에 업로드하여 실행.
-->

🚀 실행 방법
1. 로컬 환경에서 실행
GitHub에서 프로젝트를 클론합니다.
필요한 종속성을 설치합니다.
application.properties에서 포트를 8080으로 설정합니다.
java -jar 명령어로 애플리케이션을 실행합니다.
<!--
2. EC2에서 실행
AWS EC2 인스턴스를 생성하고 SSH로 접속합니다.
소스 코드를 클론하고 필요한 환경을 설정합니다.
Maven/Gradle을 사용하여 빌드한 후 JAR 파일을 실행합니다.
-->




