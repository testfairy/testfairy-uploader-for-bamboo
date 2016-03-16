package ut.com.testfairy.bamboo;

import org.junit.Test;
import com.testfairy.bamboo.api.MyPluginComponent;
import com.testfairy.bamboo.impl.MyPluginComponentImpl;

import static org.junit.Assert.assertEquals;

public class MyComponentUnitTest
{
    @Test
    public void testMyName()
    {
        MyPluginComponent component = new MyPluginComponentImpl(null);
        assertEquals("names do not match!", "myComponent",component.getName());
    }
}