package id.xxx.module.presentation

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import id.xxx.module.base.BaseInstrumentTest
import id.xxx.module.presentation.activity.MainActivity
import id.xxx.module.presentation.fragment.CallBindingInOnCreateFragment
import id.xxx.module.presentation.fragment.InitialViewInConstructorFragment
import id.xxx.module.presentation.fragment.InitialViewInCreateViewFragment
import id.xxx.module.view.binding.R
import org.junit.Rule
import org.junit.Test

class ViewBindingTest : BaseInstrumentTest() {

    @get:Rule
    var activityScenarioRule =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun view_binding_in_activity_test() {
        Espresso.onView(ViewMatchers.withId(R.id.container))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun view_binding_initial_view_in_create_view_fragment_test() {
        launchFragmentInContainer<InitialViewInCreateViewFragment>()
        Espresso.onView(ViewMatchers.withId(R.id.tv_hello))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .check(ViewAssertions.matches(ViewMatchers.withText(Utils.TEXT_HELLO_WORD)))
    }

    @Test
    fun view_binding_initial_view_in_constructor_fragment_test() {
        launchFragmentInContainer<InitialViewInConstructorFragment>()
        Espresso.onView(ViewMatchers.withId(R.id.tv_hello))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .check(ViewAssertions.matches(ViewMatchers.withText(Utils.TEXT_HELLO_WORD)))
    }

    @Test
    fun view_binding_call_in_on_create_fragment_test() {
        launchFragmentInContainer<CallBindingInOnCreateFragment>()
        Espresso.onView(ViewMatchers.withId(R.id.tv_hello))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .check(ViewAssertions.matches(ViewMatchers.withText(Utils.TEXT_HELLO_WORD)))
    }
}