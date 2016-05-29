using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RangerEntity
{
    public  class ConfirmedPin
    {
        public int id { get; set; }
        public String Latitude { get; set; }
        public String Longitude { get; set; }
       
        public DateTime ConfirmedTime { get; set; }

        public int ConfirmedBy { get; set; }
        DangerType danger = new DangerType();

       
        public int PinGroupID { get; set; }
        public String UserCreatedOn { get; set; }

        public DangerType Danger
        {
            get
            {
                return danger;
            }

            set
            {
                danger = value;
            }
        }
    }
}
