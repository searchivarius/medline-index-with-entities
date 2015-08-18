package edu.cmu.lti.oaqa.annographix.solr;

import java.util.ArrayList;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

/**
 * 
 * A simple class that transforms Pubmed-like keyword[field] constructs
 * into SOLR-annographix queries, all query parts are connected using 
 * the default operator.
 * 
 * @author Leonid Boytsov
 *
 */

public class QueryTransformer {
  public static String transform(String query) {
    Splitter ws = Splitter.on(CharMatcher.BREAKING_WHITESPACE);
    
    StringBuffer sb     = new StringBuffer();
    ArrayList<String>   parts = new ArrayList<String>();
    
    for (String s : ws.split(query)) {
      if (s.indexOf(':') > 0) { 
        appendPart(parts, sb);
        if (sb.length() > 0) sb.append(' ');
        sb.append(s);
      } else parts.add(s);
    }
    appendPart(parts, sb);
    
    return sb.toString();
  }

  private static void appendPart(ArrayList<String> parts, StringBuffer sb) {
    if (parts.isEmpty()) return;
    if (sb.length() > 0) sb.append(' ');
    
    boolean hasAnnot = false;
    
    for (String s : parts)
      if (s.charAt(s.length() - 1) == ']' &&
          s.indexOf('[') > 0) {
        hasAnnot = true;
        break;
      }

    if (!hasAnnot) {
      sb.append(Joiner.on(' ').join(parts));
    } else {
      int id = 0;
      
      sb.append("_query_:\"{!annographix ver=3} ");
      
      for (String s : parts) {
        int pos = 0;
        if (s.charAt(s.length() - 1) == ']' &&
            (pos=s.indexOf('[')) > 0) {
          String keyword = s.substring(0, pos);
          String field = s.substring(pos + 1, s.length() - 1).toLowerCase();
          sb.append(String.format(" @%d:concept_%s ~%d:%s #covers(%d,%d)",
                                    id, field, id + 1, keyword, id, id + 1));
          id += 2;
        } else sb.append(" ~:" + s);
      }
      
      sb.append("\"");      
    }
    
    parts.clear();
  }
  
}