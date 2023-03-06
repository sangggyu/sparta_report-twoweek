숙련주차 API
https://www.notion.so/API-6ae7f77edfe142b7817208ea74196851?pvs=4

포스트맨 API문서
https://documenter.getpostman.com/view/26066949/2s93JnW7XR


![image](https://user-images.githubusercontent.com/124053404/223120280-08b5e21c-a71c-4489-85a4-5a17ebef5fcc.png)



JWT를 사용하여 인증/인가를 구현 했을 때의 장점은 무엇일까요?
1. 사용자 인증에 필요한 모든 정보는 토큰 자체에 포함하기 때문에 별도의 인증 저장소가 필요 없음
2. 쿠키를 전달하지 않아도 되므로 쿠키를 사용함으로써 발생하는 취약점이 사라짐
3. URL 파라미터와 헤더로 사용
4. 트레픽에 대한 부담이 낮음
5. REST 서비스로 제공가능
6. 내장된 만료
7. 독립적

반대로 JWT를 사용한 인증/인가의 한계점은 무엇일까요?
1. 토큰 자체에 정보를 담고 있으므로 양날의 검이 될 수 있다.
2. 토큰의 페이로드에 3종류의 클레임을 저장하기 때문에, 정보가 많아질수록 토큰의 길이가 늘어나 네트워크에 부하를 줄 수 있다.
3. 중간에 Payload를 탈취당하여 디코딩하면 데이터를 볼 수 있으므로, 페이로드에 중요 데이터를 넣지 않음 (페이로드 자체는 암호화 된 것이 아니라, BASE64로 인코딩 된 것)
4. JWT는 상태를 저장하지 않기 때문에 한번 만들어지면 제어가 불가능합니다. 즉, 토큰을 임의로 삭제하는 것이 불가능하므로 토큰 만료 시간을 꼭 넣어주어야 합니다.
5. Tore Token: 토큰은 클라이언트 측에서 관리해야 하기 때문에, 토큰을 저장해야 합니다.
