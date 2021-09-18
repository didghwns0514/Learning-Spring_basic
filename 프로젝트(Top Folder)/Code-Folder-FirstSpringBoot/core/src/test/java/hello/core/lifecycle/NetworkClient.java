package hello.core.lifecycle;

public class NetworkClient {

    private String url;

    public NetworkClient() {
        // default 생성자
        System.out.println("생성자 호출 url = " + url);
        connect();
        call("초기화 연결 메세지");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작시 호출
    public void connect() {
        System.out.println("connect : " + url); // 실제 붙이지는 않고 호출만 확인
    }

    public void call(String message) {
        System.out.println("call : " + url + " message = " + message);
    }

    // 서비스 종료시 호출
    public void disconnect() {
        System.out.println("close : " + url);
    }
}
