using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RangerEntity
{
  public  class AccessToken
    {
        public int id { get; set; }
        public int TokenID { get; set; }
        User user = new User();
        public DateTime TokenizedDate { get; set; }
        public int DeviceID { get; set; }

        public User User
        {
            get
            {
                return user;
            }

            set
            {
                user = value;
            }
        }
    }
}
