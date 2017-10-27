package scheduler.job

import org.quartz.{Job, JobExecutionContext}

class Job1 extends Job {
  override def execute(context: JobExecutionContext) = {
    println("hello job!!!")
  }
}
