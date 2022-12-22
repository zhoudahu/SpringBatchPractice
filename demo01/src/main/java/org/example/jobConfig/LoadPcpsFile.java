package org.example.jobConfig;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadPcpsFile {

    @Autowired
    JobBuilderFactory jobBuilderFactory ;

    @Autowired
    StepBuilderFactory stepBuilderFactory ;

    int i = 0 ;

    @Bean
    public Job getJob1( ){
        return jobBuilderFactory.get("JobDemo")
                .start(getStep1())
//                .on("COMPLETED").to(getStep2())
                .on("FAILED").to(getStep2())
                .end()
                .build();
    }

    @Bean
    public Step getStep1( ){
        return stepBuilderFactory.get("StepDemo1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("------------getStep1------------");
                        if(i==0){
                            throw new Exception("模拟异常");
                        }
                        return RepeatStatus.FINISHED;
                    }
                }).build() ;
    }


    @Bean
    public Step getStep2( ){
        return stepBuilderFactory.get("StepDemo2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("+++++++++++getStep2++++++++++");
                        return RepeatStatus.FINISHED;
                    }
                }).build() ;
    }



}
