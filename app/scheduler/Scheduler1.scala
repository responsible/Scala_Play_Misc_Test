package scheduler

import java.util.Properties
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
  //  val scheduler = CustomizedQuartzScheduler(actorSystem)

  //  scheduler.schedule("Every2Seconds", actorSystem.actorOf(Props[MyActor]), "test")

  // ------------------
  //
  //  val scheduler: Scheduler = StdSchedulerFactory.getDefaultScheduler
  val properties: Properties = new Properties
  properties.setProperty("org.quartz.scheduler.instanceName", "dynamic-scheduler")
  properties.setProperty("org.quartz.scheduler.instanceId", "AUTO")
  properties.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool")
  properties.setProperty("org.quartz.threadPool.threadCount", "25")
  properties.setProperty("org.quartz.threadPool.threadPriority", "5")
  properties.setProperty("org.quartz.jobStore.misfireThreshold", "60000")
  properties.setProperty("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX")
  properties.setProperty("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate")
  properties.setProperty("org.quartz.jobStore.useProperties", "false")
  properties.setProperty("org.quartz.jobStore.dataSource","myDS")
  properties.setProperty("org.quartz.jobStore.tablePrefix","QRTZ_")
  properties.setProperty("org.quartz.jobStore.isClustered","true")
  properties.setProperty("org.quartz.jobStore.clusterCheckinInterval","5000")
  properties.setProperty("org.quartz.dataSource.myDS.driver","org.postgresql.Driver")
  properties.setProperty("org.quartz.dataSource.myDS.URL","jdbc:postgresql://localhost:5432/play_test")
  properties.setProperty("org.quartz.dataSource.myDS.user","postgres")
  properties.setProperty("org.quartz.dataSource.myDS.password","postgres")
  properties.setProperty("org.quartz.dataSource.myDS.maxConnections","5")
  properties.setProperty("org.quartz.dataSource.myDS.validationQuery","select 0 from dual")
  val scheduler: Scheduler = (new StdSchedulerFactory(properties)).getScheduler

  scheduler.start()

  //   define the job and tie it to our HelloJob class
  val job: JobDetail = newJob(classOf[Job1]).withIdentity("job1", "group1").build

  // Trigger the job to run now, and then repeat every 40 seconds
  val trigger: Trigger = newTrigger.withIdentity("trigger1", "group1").startNow.withSchedule(simpleSchedule.withIntervalInSeconds(40).repeatForever).build

  // Tell quartz to schedule the job using our trigger
  scheduler.scheduleJob(job, trigger)

  scheduler.shutdown
}
