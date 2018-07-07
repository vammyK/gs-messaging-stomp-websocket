from elasticsearch import Elasticsearch
from image_match.elasticsearch_driver import SignatureES
import sys
es = Elasticsearch()
ses = SignatureES(es)
data=ses.search_image(sys.argv[1],all_orientations=True)
print(data)