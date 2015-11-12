# pubmed-index-with-entities
Basic indexing sequence (**need to be more detailed**):
``
scripts/lucene_index.sh <bioconcept annotation file> <Lucene index directory>
``

Annotation **need to update CPE parameters**:
``
scripts/cpe.sh src/main/resources/descriptors/collection_processing_engines/cpeMain.xml 2>&1|tee out.log
``

Finally indexing using SOLR (*need to set up a SOLR instance first**):
``
run/solr_indexer.sh ../../medline-index-with-entities/output/text.txt.gz ../../medline-index-with-entities/output/annot_offsets.tsv.gz http://<server address>:<server port>/solr/medline Text4Annotation Annotation
``
