using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RangerEntity
{
    public  class Contact
    {
        public int id { get; set; }
        DangerType dangeType = new DangerType();
        public String Latitude { get; set; }
        public String Longitude { get; set; }
        public String Number1 { get; set; }
        public String Number2 { get; set; }
        public String Number3 { get; set; }

        public DangerType DangeType
        {
            get
            {
                return dangeType;
            }

            set
            {
                dangeType = value;
            }
        }
    }
}
