import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.Rule;

import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Salon Scheduler");
  }

  @Test
  public void createNewStylistLastName() {
    goTo("http://localhost:4567/");
    click("a", withText("Manage Stylists"));
    fill("#stylistLastName").with("Styles");
    submit(".btn");
    assertThat(pageSource()).contains("Styles");
  }

  @Test
  public void createNewStylistFirstName() {
    goTo("http://localhost:4567/");
    click("a", withText("Manage Stylists"));
    fill("#stylistFirstName").with("Julia");
    submit(".btn");
    assertThat(pageSource()).contains("Julia");
  }

  @Test
  public void addClientFormAddsClient() {
    Stylist testStylist = new Stylist ("Styles", "Julia");
    testStylist.save();
    String stylistPath = String.format("http://localhost:4567/stylists/%d", testStylist.getId());
    goTo(stylistPath);
    fill("#newClientLastName").with("Poovey");
    submit("#addClient");
    assertThat(pageSource()).contains("Poovey");
  }
}
