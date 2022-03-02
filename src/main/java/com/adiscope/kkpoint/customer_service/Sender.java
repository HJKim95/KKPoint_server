package com.adiscope.kkpoint.customer_service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Sender {

    public void send(SenderDto senderDto){
        try {
            log.info("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");
            // 아래 부분에 위에서 받은 ID, Key를 집어 넣습니다.
            // 인증방식은 제가 고쳐서 진행했습니다.
            BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAUS2RWYGL6MCFAVBD","YnTY6IsP6kzk97tLzjd+hvZhG4jHSPLErQFw8cap");
            AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(awsCreds);
            try {
                // 아래와 같이 인증방식을 변경함.
                credentialsProvider.getCredentials();
            } catch (Exception e) {
                throw new AmazonClientException(
                        "Cannot load the credentials from the credential profiles file. " +
                                "Please make sure that your credentials file is at the correct " +
                                "location (~/.aws/credentials), and is in valid format.",
                        e);
            }

            AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
                    .withCredentials(credentialsProvider)
                    // 자신이 설정한 리전으로 변경할 것!!
                    .withRegion("ap-northeast-2")
                    .build();

            // Send the email.
            client.sendEmail(senderDto.toSendRequestDto());
            log.info("Email sent!");

        } catch (Exception ex) {
            log.error("The email was not sent.");
            log.error("Error message: " + ex.getMessage());
            throw new AmazonClientException(
                    ex.getMessage(),
                    ex);
        }
    }
}
