package com.salt.prototype.springbatch.job

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StepNextJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Bean
    fun stepNextJob(): Job {
        return jobBuilderFactory["stepNextJob"]
            .start(this.step1())
            .next(this.step2())
            .next(this.step3())
            .build()
    }

    @Bean
    fun step1(): Step {
        return stepBuilderFactory["step1"]
            .tasklet{contribution, chunkContext ->
                log.info(">>>>>>> This is Step1")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun step2(): Step {
        return stepBuilderFactory["step2"]
            .tasklet{
                contribution, chunkContext ->
                log.info(">>>>>>> This is Step2")
                RepeatStatus.FINISHED
            }
            .build()
    }

    fun step3(): Step {
        return stepBuilderFactory["step3"]
            .tasklet {
                contribution, chunkContext ->
                log.info(">>>>>>> This is Step2")
                RepeatStatus.FINISHED
            }
            .build()
    }


}