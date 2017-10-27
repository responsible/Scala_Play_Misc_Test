package scheduler

import java.util.Date

import akka.actor.{ActorSystem, ExtendedActorSystem, ExtensionKey}
import akka.event.Logging
import com.typesafe.akka.extension.quartz.{QuartzSchedule, QuartzSchedulerExtension, SimpleActorMessageJob}
import org.quartz.JobBuilder
import org.quartz.core.jmx.JobDataMapSupport
import org.quartz.impl.StdSchedulerFactory

object CustomizedQuartzScheduler extends ExtensionKey[CustomizedQuartzScheduler] {
  override def get(system: ActorSystem): CustomizedQuartzScheduler = super.get(system)
}

class CustomizedQuartzScheduler(system: ExtendedActorSystem) extends QuartzSchedulerExtension(system: ExtendedActorSystem) {
  private val log = Logging(system, this)

  override protected def scheduleJob(name: String, receiver: AnyRef, msg: AnyRef, startDate: Option[Date])(schedule: QuartzSchedule): Date = {
    import scala.collection.JavaConverters._
//    log.info("Setting up scheduled job '{}', with '{}'", name, schedule)
    val jobDataMap = Map[String, AnyRef](
      "logBus" -> system.eventStream,
      "receiver" -> receiver,
      "message" -> msg
    )

    val jobData = JobDataMapSupport.newJobDataMap(jobDataMap.asJava)
    val job = JobBuilder.newJob(classOf[SimpleActorMessageJob])
      .withIdentity(name + "_Job")
      .usingJobData(jobData)
      .withDescription(schedule.description.orNull)
      .build()

//    log.debug("Adding jobKey {} to runningJobs map.", job.getKey)

    runningJobs += name -> job.getKey

//    log.debug("Building Trigger with startDate '{}", startDate.getOrElse(new Date()))
    val trigger = schedule.buildTrigger(name, startDate)

//    log.debug("Scheduling Job '{}' and Trigger '{}'. Is Scheduler Running? {}", job, trigger, scheduler.isStarted)
    scheduler.scheduleJob(job, trigger)
  }

  override lazy protected val scheduler = {
    val scheduler = StdSchedulerFactory.getDefaultScheduler

//    log.debug("Initialized a Quartz Scheduler '{}'", scheduler)
    system.registerOnTermination({
//      log.info("Shutting down Quartz Scheduler with ActorSystem Termination (Any jobs awaiting completion will end as well, as actors are ending)...")
      scheduler.shutdown(false)
    })

    scheduler
  }
}
