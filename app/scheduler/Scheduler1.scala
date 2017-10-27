package scheduler

import javax.inject.Inject

import actor.MyActor
import akka.actor.{ActorSystem, Props}
import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension
import org.quartz.JobBuilder.newJob
import org.quartz.SimpleScheduleBuilder.simpleSchedule
import org.quartz.TriggerBuilder.newTrigger
import org.quartz.{JobDetail, Scheduler, Trigger}
import org.quartz.impl.StdSchedulerFactory
import scheduler.job.Job1

class Scheduler1 @Inject()(actorSystem: ActorSystem) {
  println("startup scheduler!")

//  val scheduler = QuartzSchedulerExtension(actorSystem)
  val scheduler = CustomizedQuartzScheduler(actorSystem)

  scheduler.schedule("Every2Seconds", actorSystem.actorOf(Props[MyActor]), "test")

// ------------------
//
//  val scheduler: Scheduler = StdSchedulerFactory.getDefaultScheduler
//  scheduler.start()
//
//  //   define the job and tie it to our HelloJob class
//  val job: JobDetail = newJob(classOf[Job1]).withIdentity("job1", "group1").build
//
//  // Trigger the job to run now, and then repeat every 40 seconds
//  val trigger: Trigger = newTrigger.withIdentity("trigger1", "group1").startNow.withSchedule(simpleSchedule.withIntervalInSeconds(40).repeatForever).build
//
//  // Tell quartz to schedule the job using our trigger
//  scheduler.scheduleJob(job, trigger)
//
//  scheduler.shutdown
}
