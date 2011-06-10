package org.synyx.minos.i18n.web;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DefaultLocaleEditorUnitTest {

    private DefaultLocaleEditor defaultLocaleEditor;

    @Before
    public void setUp() {
        defaultLocaleEditor = new DefaultLocaleEditor();
    }

    @Test(expected = NullPointerException.class)
    public void testTextNull() {
        defaultLocaleEditor.setAsText(null);
    }

    @Test
    public void testTextEmpty() {
        defaultLocaleEditor.setAsText("");

        Assert.assertEquals("", defaultLocaleEditor.getAsText());
    }

    @Test
    public void testTextDefault() {
        defaultLocaleEditor.setAsText("default");

        Assert.assertEquals("", defaultLocaleEditor.getAsText());
    }

    @Test
    public void testTextArbitrary() {
        String text = "foobar";
        defaultLocaleEditor.setAsText(text);

        Assert.assertEquals(text, defaultLocaleEditor.getAsText());
    }
}
