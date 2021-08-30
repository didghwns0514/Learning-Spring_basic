package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        // Configuration을 없애서 원본 AppConfig 파일에 @Configuration 수동 등록과 충돌 방지
        // @configuration 하위에 @Component 어노테이션이 존재해서, 자동으로 스캔 범위에 포함되어버림
        // 보통 설정 정보를 컴포넌트 스캔 대상에서 제외하지는 않지만, 기존 예제 코드를 최대한 남기고 유지하기 위해서 이 방법을 선택
        excludeFilters = @ComponentScan.Filter(type=FilterType.ANNOTATION, classes=Configuration.class)
)
public class AutoAppConfig {

}
