package com.example.novigradv2;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import com.example.novigradv2.employeeActivities.CreateBranchActivity;
import org.junit.Rule;
import org.junit.Test;

public class CreateBranchActivityTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void schedule_isNotValidEmail()  {
        CreateBranchActivity c = new CreateBranchActivity();
        boolean result = c.validateWorkScheduleInput("12","30","2","3");
        assertThat(result, is(false));
    }

    @Test
    public void schedule_isValidEmail(){
        CreateBranchActivity c = new CreateBranchActivity();
        boolean result = c.validateWorkScheduleInput("12","19","2","3");
        assertThat(result, is(true));
    }
}
