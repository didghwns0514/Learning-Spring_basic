package hello.core.singleton;

public class SingletonService {

    // 자기 자신을 내부에 private으로 static하게 가지고있음
    // 관례상 밑에처럼 작성
    // 클래스 level에 올라가기 때문에 단 하나만 존재하게 됨; 자바 기본의 Static에 대해서 공부해볼 것
    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    private SingletonService() {

    }

    public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
    }

}
