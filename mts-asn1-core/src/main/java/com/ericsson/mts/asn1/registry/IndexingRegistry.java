/*
 * Copyright 2019 Ericsson, https://www.ericsson.com/en
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.ericsson.mts.asn1.registry;

import com.ericsson.mts.asn1.ASN1Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Use to store context from .asn files which is parse with TopLevelVisitor
 */
class IndexingRegistry {
    private Map<String, ASN1Parser.ValueAssignmentContext> valueindicingregistry = new ConcurrentHashMap<>();
    private Map<String, ASN1Parser.TypeAssignmentContext> typeindicingregistry = new ConcurrentHashMap<>();
    private Map<String, ASN1Parser.ParameterizedAssignmentContext> parameterizedindicingregistry = new ConcurrentHashMap<>();
    private Map<String, ASN1Parser.ObjectClassAssignmentContext> objectclassindicingregistry = new ConcurrentHashMap<>();
    private Map<String, ASN1Parser.ObjectAssignmentContext> objectAssignmentContextHashMap = new ConcurrentHashMap<>();
    private Logger logger = LoggerFactory.getLogger(IndexingRegistry.class.getSimpleName());

    void addAssignment(ASN1Parser.AssignmentContext assignmentContext) {
        if (null != assignmentContext.valueAssignment()) {
            valueindicingregistry.put(assignmentContext.IDENTIFIER().getText(), assignmentContext.valueAssignment());
        } else if (null != assignmentContext.typeAssignment()) {
            typeindicingregistry.put(assignmentContext.IDENTIFIER().getText(), assignmentContext.typeAssignment());
        } else if (null != assignmentContext.parameterizedAssignment()) {
            parameterizedindicingregistry.put(assignmentContext.IDENTIFIER().getText(), assignmentContext.parameterizedAssignment());
        } else if (null != assignmentContext.objectAssignment()) {
            objectAssignmentContextHashMap.put(assignmentContext.IDENTIFIER().getText(), assignmentContext.objectAssignment());
        } else {
            objectclassindicingregistry.put(assignmentContext.IDENTIFIER().getText(), assignmentContext.objectClassAssignment());
        }
    }

    ASN1Parser.ValueAssignmentContext getConstantContext(String identifier) {
        logger.trace("Parse " + identifier + " as a constant");
        return valueindicingregistry.remove(identifier);
    }

    ASN1Parser.TypeAssignmentContext getTranslatorContext(String identifier) {
        logger.trace("Parse " + identifier + " as a translator");
        return typeindicingregistry.remove(identifier);
    }

    ASN1Parser.ParameterizedAssignmentContext getParameterizedAssignementContext(String identifier) {
        logger.trace("Parse " + identifier + " as a parameterized assignment");
        return parameterizedindicingregistry.remove(identifier);
    }

    ASN1Parser.ObjectAssignmentContext getObjectContext(String identifier) {
        logger.trace("Parse " + identifier + " as an object");
        return objectAssignmentContextHashMap.remove(identifier);
    }

    ASN1Parser.ObjectClassAssignmentContext getClassHandlerContext(String identifier) {
        logger.trace("Parse " + identifier + " as a class");
        return objectclassindicingregistry.remove(identifier);
    }

    /***** Use to pre-parse ASN.1 object *****/

    List<String> getConstantsIdentifier() {
        Iterator it = valueindicingregistry.keySet().iterator();
        List<String> arrayList = new ArrayList<>();
        while (it.hasNext()) {
            arrayList.add((String) it.next());
        }
        return arrayList;
    }

    List<String> getTranslatorsIdentifier() {
        Iterator it = typeindicingregistry.keySet().iterator();
        List<String> arrayList = new ArrayList<>();
        while (it.hasNext()) {
            arrayList.add((String) it.next());
        }
        return arrayList;
    }

    List<String> getParameterizedTranslatorsIdentifier() {
        Iterator it = parameterizedindicingregistry.keySet().iterator();
        List<String> arrayList = new ArrayList<>();
        while (it.hasNext()) {
            String key = (String) it.next();
            if (parameterizedindicingregistry.get(key).asnType() != null) {
                arrayList.add(key);
            }
        }
        return arrayList;
    }

    List<String> getClassHandlersdentifier() {
        Iterator it = objectclassindicingregistry.keySet().iterator();
        List<String> arrayList = new ArrayList<>();
        while (it.hasNext()) {
            arrayList.add((String) it.next());
        }
        return arrayList;
    }

    List<String> getObjectSetAssignment() {
        Iterator it = parameterizedindicingregistry.keySet().iterator();
        List<String> arrayList = new ArrayList<>();
        while (it.hasNext()) {
            String key = (String) it.next();
            if (parameterizedindicingregistry.get(key).objectSet() != null) {
                arrayList.add(key);
            }
        }
        return arrayList;
    }

    List<String> getObjectsContextdentifier() {
        Iterator it = objectAssignmentContextHashMap.keySet().iterator();
        List<String> arrayList = new ArrayList<>();
        while (it.hasNext()) {
            arrayList.add((String) it.next());
        }
        return arrayList;
    }

    /**
     * @return true if all contexts are consume, false otherwise
     */
    boolean checkRegistry() {
        return valueindicingregistry.isEmpty() && typeindicingregistry.isEmpty() && parameterizedindicingregistry.isEmpty() && objectclassindicingregistry.isEmpty() && objectAssignmentContextHashMap.isEmpty();
    }
}
