package test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestApplicationTest {
  @Test
  void contextLoads() {}
  
  @Test
  public void main() {
	  TestApplication.main(new String[] {});
  }
}