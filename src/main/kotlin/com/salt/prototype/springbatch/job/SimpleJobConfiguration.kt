package com.salt.prototype.springbatch.job

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.lang.IllegalArgumentException

@Configuration
class SimpleJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {

    @Bean
    fun simpleJob(): Job {
        return jobBuilderFactory["simpleJob"]
            .start(this.simpleStep1(null))
            .next(this.simpleStep2(null))
            .build()
    }

    @Bean
    @JobScope
    fun simpleStep1(@Value("#{jobParameters[requestDate]}") requestDate: String?): Step {
        return stepBuilderFactory["simpleStep1"]
            .tasklet { contribution: StepContribution, chunkContext: ChunkContext ->
                log.info(">>>>> This is Step1")
                log.info(">>>>> requestDate = {}", requestDate)
                //throw IllegalArgumentException("step1에서 실패합니다.")
                RepeatStatus.FINISHED
            }
            .build()
    }


    fun simpleStep2(@Value("#{jobParameters[requestDate]}") requestDate: String?): Step {
        return stepBuilderFactory["simpleStep2"]
            .tasklet { contribution, chunkContext ->
                log.info(">>>>> This is Step2")
                log.info(">>>>> requestDate = {}", requestDate)
                RepeatStatus.FINISHED
            }
            .build()
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }
}