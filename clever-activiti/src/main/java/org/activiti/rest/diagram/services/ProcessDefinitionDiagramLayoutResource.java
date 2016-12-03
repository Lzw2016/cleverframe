package org.activiti.rest.diagram.services;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 参考: activiti-diagram-rest<br/>
 * 作者：LiZW <br/>
 * 创建时间：2016/12/3 20:34 <br/>
 */
@RestController
public class ProcessDefinitionDiagramLayoutResource extends BaseProcessDefinitionDiagramLayoutResource {

    @RequestMapping(value = "/process-definition/{processDefinitionId}/diagram-layout", method = RequestMethod.GET, produces = "application/json")
    public ObjectNode getDiagram(@PathVariable String processDefinitionId) {
        return getDiagramNode(null, processDefinitionId);
    }
}

