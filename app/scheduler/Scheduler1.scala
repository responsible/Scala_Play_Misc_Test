package scheduler

import java.util.Properties
import javax.inject.Inject

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import org.quartz.JobBuilder.newJob
import org.quartz.SimpleScheduleBuilder.simpleSchedule
import org.quartz.TriggerBuilder.newTrigger
import org.quartz.impl.StdSchedulerFactory
import org.quartz.{JobDetail, Scheduler, Trigger}
import scheduler.job.Job1

class Scheduler1 @Inject()(actorSystem: ActorSystem) {
  println("startup scheduler!")

  //  val scheduler: Scheduler = StdSchedulerFactory.getDefaultScheduler

  // This can be changed to a system env variable then load that conf file
  val conf = ConfigFactory.parseResources("quartz.properties")

  val properties: Properties = new Properties
  conf.entrySet().forEach(e => properties.setProperty(e.getKey, conf.getString(e.getKey)))

  // We can override the properties like password due to security reason
  properties.setProperty("org.quartz.dataSource.myDS.password", "secret")

  val scheduler: Scheduler = (new StdSchedulerFactory(properties)).getScheduler

  scheduler.start()

  // define the job and tie it to our HelloJob class
  val job: JobDetail = newJob(classOf[Job1]).withIdentity("job1", "group1").build

  // Trigger the job to run now, and then repeat every 40 seconds
  val trigger: Trigger = newTrigger.withIdentity("trigger1", "group1").startNow.withSchedule(simpleSchedule.withIntervalInSeconds(40).repeatForever).build

  // Tell quartz to schedule the job using our trigger
  scheduler.scheduleJob(job, trigger)

  scheduler.shutdown
}
