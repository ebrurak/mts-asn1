/*
 * Copyright 2019 Ericsson, https://www.ericsson.com/en
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.ericsson.mts.asn1;

import com.ericsson.mts.asn1.factory.AbstractTranslatorFactory;
import com.ericsson.mts.asn1.translator.*;

public class PERTranslatorFactory extends AbstractTranslatorFactory {

    private PERTranscoder perTranscoder;

    public PERTranslatorFactory(boolean aligned) {
        perTranscoder = new PERTranscoder(aligned);
    }

    @Override
    public AbstractBitStringTranslator bitStringTranslator() {
        return new PERBitStringTranslator(perTranscoder);
    }

    @Override
    public AbstractBooleanTranslator booleanTranslator() {
        return new PERBooleanTranslator();
    }

    @Override
    public AbstractChoiceTranslator choiceTranslator() {
        return new PERChoiceTranslator(perTranscoder);
    }

    @Override
    public AbstractEnumeratedTranslator enumeratedTranslator() {
        return new PEREnumeratedTranslator(perTranscoder);
    }

    @Override
    public AbstractIntegerTranslator integerTranslator() {
        return new PERIntegerTranslator(perTranscoder);
    }

    @Override
    public AbstractOctetStringTranslator octetStringTranslator() {
        return new PEROctetStringTranslator(perTranscoder);
    }

    @Override
    public AbstractRealTranslator realTranslator() {
        return new PERRealTranslator(perTranscoder);
    }

    @Override
    public AbstractRestrictedCharacterStringTranslator characterStringTranslator() {
        return new PERRestrictedCharacterStringTranslator(perTranscoder);
    }

    @Override
    public AbstractSequenceOfTranslator sequenceOfTranslator() {
        return new PERSequenceOfTranslator(perTranscoder);
    }

    @Override
    public AbstractSequenceTranslator sequenceTranslator() {
        return new PERSequenceTranslator(perTranscoder);
    }

    @Override
    public AbstractObjectClassFieldTranslator objectClassFieldTypeTranslator() {
        return new PERObjectClassFieldTranslator(perTranscoder);
    }

    @Override
    public AbstractObjectIdentifierTranslator objectIdentifierTranslator() {
        return new PERObjectIdentifierTranslator(perTranscoder);
    }
}
