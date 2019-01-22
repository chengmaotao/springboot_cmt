import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.Application;
import com.app.service.WxService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestAPP {

	@Autowired
	private WxService wxService;

	@Test
	public void test1() {

		String token = wxService.getAccessToken();
		System.out.println(token);
	}

}
