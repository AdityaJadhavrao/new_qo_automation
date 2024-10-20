package automation_Test;

import org.testng.annotations.Test;
import java.io.IOException;

import org.testng.annotations.Test;


import try_automation.BaseClass;
import try_automation.Build_Page;
import try_automation.Shell_script;

public class AutomationTest extends BaseClass{
	Build_Page object;
	Shell_script object1;
	
	@Test
	public void build() throws InterruptedException
	{
		object = new Build_Page();
		object.build();
		object.downloadBuild();
		object.validateThePage();
	}
	@Test
	public void execute_shell_script() throws IOException, InterruptedException
	{
		object1 = new Shell_script();
		object1.run_shell_script();
	}
}
