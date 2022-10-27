# alicorn-test

## 기술 스택
- 언어 : Kotlin
- DI : Hilt
- 비동기 : Coroutine
- 네트워크 : Retrofit2, OkHttp3
- Socket : Stomp

## 프로젝트 구조
- 클린 아키텍처 기반의 멀티모듈 프로젝트
- MVVM 디자인 패턴 적용

## 프로젝트 설명
- API가 구현되어 있지 않아 **fake가 포함되어 있는 url path**가 들어온다면 mock 데이터를 반환하도록 ***MockDataManager.kt***를 구현
  - 채팅방 리스트, 채팅 리스트
  - 연결된 사람
  - 로그인
- ServerSocket이 열려있다고 가정하고 ClientSocket을 구현 (WebSocket 기반의 Stomp 이용)
- AccountManager를 이용하여 계정 정보 저장
  - MockDataManager.kt에 ***members*** 리스트로 가입된 회원 임시 확인
    - kim / 1234
    - lee / 1234
    - kang / 1212
    - park / 4321
    - kim2 / 1111
- ***NewChatViewModel.kt***(새 메시지 화면 관련 ViewModel)에서 MediatorLiveData를 사용하여 받는 사람 글자가 입력 될 때마다 연결된 사람 api 요청
  
