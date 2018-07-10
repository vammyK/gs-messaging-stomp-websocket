from elasticsearch import Elasticsearch
from image_match.elasticsearch_driver import SignatureES
import sys
es = Elasticsearch()
ses = SignatureES(es)
ses.add_image(sys.argv[1],metadata= sys.argv[2])
