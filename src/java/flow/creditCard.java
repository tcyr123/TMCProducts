package flow;

import java.io.Serializable;
import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;

public class creditCard implements Serializable
{

    @Produces
    @FlowDefinition
    public Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder)
    {

        String flowId = "creditCard";
        flowBuilder.id("", flowId);
        flowBuilder.viewNode(flowId, "/" + flowId + "/" + flowId + ".xhtml").markAsStartNode();
        flowBuilder.viewNode("thanks-id", "/" + flowId + "/thanks.xhtml");

        flowBuilder.returnNode("taskFlowReturnIndex").fromOutcome("#{creditCardBean.cartValue}");
        flowBuilder.returnNode("taskFlowReturnDone").fromOutcome("#{creditCardBean.returnValue}");
//        flowBuilder.returnNode("taskFlowReturnError").fromOutcome("/error");
//        flowBuilder.returnNode("taskFlowReturnFailed").fromOutcome("/failed");

//        flowBuilder.flowCallNode("callOrientation").flowReference("", "orientation").
//                outboundParameter("firstNameParam", "#{registrationBean.firstName}").
//                outboundParameter("lastNameParam", "#{registrationBean.lastName}").
//                outboundParameter("studentRegistrationCode", "A1FS");

        return flowBuilder.getFlow();
    }
}

