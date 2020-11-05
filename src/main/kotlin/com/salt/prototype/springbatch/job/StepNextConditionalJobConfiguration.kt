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
class StepNextConditionalJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Bean
    fun stepNextConditionalJob(): Job {
        return jobBuilderFactory.get("stepNextConditionalJob")
            .start(this.conditionalJobStep1())
                .on("FAILED")
                .to(this.conditionalJobStep3())
                .on("*")
                .end()
            .from(this.conditionalJobStep1())
                .on("*")
                .to(this.conditionalJobStep2())
                .next(this.conditionalJobStep3())
                .on("*")
                .end()
            .end()
            .build()
    }

    @Bean
    fun conditionalJobStep1(): Step {
        return stepBuilderFactory["step1"]
            .tasklet { contribution, chunkContext ->
                log.info(">>>>> This is stepNextConditionalJob Step1")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun conditionalJobStep2(): Step {
        return stepBuilderFactory["step2"]
            .tasklet { contribution, chunkContext ->
                log.info(">>>>> This is stepNextConditionalJob Step2")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun conditionalJobStep3(): Step {
        return stepBuilderFactory["step3"]
            .tasklet { contribution, chunkContext ->
                log.info(">>>>> This is stepNextConditionalJob Step3")
                RepeatStatus.FINISHED
            }
            .build()
    }
}