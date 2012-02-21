/* $Id$ */
package hms.common.gui.filters;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Jackson Lamp (jal2633)
 */
public class NumericDocumentFilter extends DocumentFilter {
    private Document document;
    private boolean allowDecimalPoint = false;
    private boolean allowNegatives = true;

    public NumericDocumentFilter(Document document, boolean allowDecimalPoint, boolean allowNegatives) {
        super();
        this.document = document;
        this.allowDecimalPoint = allowDecimalPoint;
        this.allowNegatives = allowNegatives;
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        boolean valid = true;
        int position = offset;

        for(char c : text.toCharArray()) {
            /* Valid input:
             * - '0' - '9'
             * - '-' if it is the first character
             * - '.' if '.' does not yet exist in the document
             */
            valid = valid && (c <= '9' && c >= '0' ||
                    (this.allowNegatives && c == '-' && position == 0) ||
                    (this.allowDecimalPoint && c == '.' && !getRemainingDocumentText(offset, length).contains(".")));
            position++;
        }

        if(valid) {
            super.replace(fb, offset, length, text, attrs);
        }
    }
    
    private String getRemainingDocumentText(int offset, int length) throws BadLocationException {
        String text = this.document.getText(0, this.document.getLength());
        String preOffset = text.substring(0, offset);
        String postOffset = text.substring(offset + length);
        text = preOffset + postOffset;
        
        return text;
    }
}
