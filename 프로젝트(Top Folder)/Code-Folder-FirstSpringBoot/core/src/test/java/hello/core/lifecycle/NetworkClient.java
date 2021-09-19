package hello.core.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {

    private String url;

    public NetworkClient() {
        // default 생성자
        System.out.println("생성자 호출 url = " + url);

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

    @PostConstruct
    public void init() {
        // 의존관계 주입이 끝나면 호출해준다는 뜻
        System.out.println("NeworkClient.afterPropertiesSet");
        connect();
        call("초기화 연결 메세지");
    }

    @PreDestroy
    public void close() {
        // 작업 완료 이후, 종료 이전에 수행해야할 것들을 호출해주는 부분
        System.out.println("NetworkClient.destroy");
        disconnect();
    }
}
